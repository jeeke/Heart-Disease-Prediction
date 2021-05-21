// eslint-disable-next-line @typescript-eslint/no-unused-vars
import {BaseEntity, Column, Entity, PrimaryGeneratedColumn} from "typeorm/index";

@Entity()
export class Video extends BaseEntity {

    @PrimaryGeneratedColumn()
    id: string;

    @Column()
    title: string;

    @Column()
    videoId: string;

    @Column()
    thumbnail: string;

    @Column()
    lang: string;
}
