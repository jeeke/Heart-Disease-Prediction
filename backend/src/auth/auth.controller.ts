import {Body, Controller, Post, ValidationPipe} from '@nestjs/common';
import {AuthService} from "./auth.service";
import {LoginBody} from "./dto/loginBody";
import {VerifyOtpBody} from "./dto/verifyOtpBody";

@Controller('/api')
export class AuthController {
    constructor(private authService: AuthService) {
    }

    @Post("/login")
    login(@Body(ValidationPipe) loginDto: LoginBody) {
        return this.authService.login(loginDto);
    }

    @Post("/verify-otp")
    verifyOtp(@Body(ValidationPipe) verifyOtpBody: VerifyOtpBody) {
        return this.authService.verifyOtp(verifyOtpBody);
    }
}
