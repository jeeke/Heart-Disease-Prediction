import {ResourceWithOptions} from "admin-bro";
import {User} from "../../models/user.entity";
import * as bcrypt from "bcryptjs"
import {UserType} from "../../auth/jwt-payload.interface";

const middleware = async (request) => {
    if (request.payload.hashed_password && request.payload.user_type === UserType.Admin) {
        request.payload = {
            ...request.payload,
            hashed_password: await bcrypt.hash(request.payload.hashed_password, 10),
        }
    }
    return request
};
const UserResource: ResourceWithOptions = {
    resource: User,
    options: {
        actions: {
            new: {
                before: middleware,
            },
            edit: {
                before: middleware,
            }
        }
    },
};

export default UserResource;
