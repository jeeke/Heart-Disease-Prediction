import {Injectable, UnauthorizedException} from "@nestjs/common";
import {JwtService} from "@nestjs/jwt";
import {User} from "../models/user.entity";
import {JwtPayload} from "./jwt-payload.interface";
import {LoginBody} from "./dto/loginBody";
import {VerifyOtpBody} from "./dto/verifyOtpBody";

@Injectable()
export class AuthService {
    constructor(private jwtService: JwtService
    ) {
    }

    async login(loginDto: LoginBody): Promise<any> {
        let user = await User.findOne({
            where: {
                phone: loginDto.phone
            }
        });
        const new_user = !user

        //create new user
        if (new_user) {
            user = new User();
            user.phone = loginDto.phone
        }

        user.otp = Math.floor(1000 + Math.random() * 9000);
        user.otp_sent_at = new Date()


        const p1 = user.save();

        //send otp
        const p2 = await this.sendOtp(user)

        const res = await Promise.all([p1, p2])
        return {
            new_user,
            id: res[0].id,
            otp: res[0].otp
        }
    }

    async sendOtp(user: User) {

    }

    async verifyOtp(verifyOtpDto: VerifyOtpBody) {
        //find user
        const user: User = await User.findOne(verifyOtpDto.id);

        //check expiry
        const dateDiff = Math.abs(new Date().getTime() - user.otp_sent_at.getTime());
        const dateDiffMin = dateDiff / 60000


        if (user.otp == verifyOtpDto.otp && dateDiffMin < 10) {
            const payload: JwtPayload = {id: user.id};
            const token = await this.jwtService.sign(payload);
            return {token}
        }
        return new UnauthorizedException();
    }

}
