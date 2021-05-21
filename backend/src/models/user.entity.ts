import {BaseEntity, Column, Entity, PrimaryGeneratedColumn, Unique} from "typeorm/index";

@Entity()
@Unique(["phone"])
export class User extends BaseEntity {
    @PrimaryGeneratedColumn()
    id: string;

    @Column()
    phone: string;

    @Column({nullable: true})
    user_type: string;

    @Column({nullable: true})
    name: string;

    @Column({nullable: true})
    otp_sent_at: Date;

    @Column({nullable: true})
    otp_resent_count: number;

    @Column({nullable: true})
    otp: number
}
