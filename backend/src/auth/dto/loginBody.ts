import {IsEmail, IsPhoneNumber, IsString} from "class-validator";
import {ApiProperty} from "@nestjs/swagger";

export class LoginBody {

    @ApiProperty()
    @IsPhoneNumber(null)
    phone: string;

    @ApiProperty()
    device_data: Object;

}
