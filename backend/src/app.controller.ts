import {Controller, Get, Query} from '@nestjs/common';
import {Article} from "./models/article.entity";
import {Video} from "./models/video.entity";

@Controller()
export class AppController {

    @Get('api')
    getHello() {
        return {message: "Welcome to Heart Disease Predictor API!"}
    }

    @Get('api/articles')
    getAllArticles(@Query('lang') lang: string = 'en') {
        return Article.find({
            where: {
                lang: lang
            }
        })
    }

    @Get('api/videos')
    getAllVideos(@Query('lang') lang: string = 'en') {
        return Video.find({
            where: {
                lang: lang
            }
        })
    }

}
