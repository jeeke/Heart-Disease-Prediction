import {Module} from "@nestjs/common";
import {AuthController} from "./auth.controller";
import {AuthService} from "./auth.service";
import {PassportModule} from "@nestjs/passport";
import {JwtModule} from "@nestjs/jwt";
import * as config from "config";
import {JwtStrategy} from "./jwt.strategy";

const jwtConfig = config.get("jwt");

@Module({
    imports: [
        PassportModule.register({defaultStrategy: "jwt"}),
        JwtModule.register({
            secret: process.env.JWT_SECRET || jwtConfig.secret,
            // signOptions: {
            //     expiresIn: jwtConfig.expiresIn
            // }
        }),
    ],
    controllers: [AuthController],
    providers: [AuthService, JwtStrategy],
    exports: [JwtModule]
})
export class AuthModule {
}