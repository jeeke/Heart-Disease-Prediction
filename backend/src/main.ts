import {NestFactory} from "@nestjs/core";
import {Logger} from "@nestjs/common";
import {AppModule} from "./app.module";
import * as config from "config";
import {setupAdminPanel} from "./admin/admin.plugin";
import {DocumentBuilder, SwaggerModule} from "@nestjs/swagger";
import * as bodyParser from 'body-parser';

async function bootstrap() {
    const serverConfig = config.get("server");
    const logger = new Logger("bootstrap");
    const app = await NestFactory.create(AppModule);
    await setupAdminPanel(app);

    // if(process.env.NODE_ENV === 'development'){
    const options = new DocumentBuilder()
        .setTitle("Itihas")
        .setDescription("Itihas REST API description")
        .setVersion("1.0")
        .addBearerAuth()
        .build();
    const document = SwaggerModule.createDocument(app, options);
    SwaggerModule.setup("api/docs", app, document);
    // }
    app.use(bodyParser.json({limit: '50mb'}));
    app.use(bodyParser.urlencoded({limit: '50mb', extended: true}));
    app.enableCors();
    // if (process.env.NODE_ENV === "development") {
    //   app.enableCors();
    // } else {
    //   app.enableCors({ origin: serverConfig.origin });
    //   logger.log(`Accepting requests from origin "${serverConfig.origin}"`);
    // }
    const port = process.env.PORT || serverConfig.port;
    await app.listen(port);
    logger.log(`Application listening on port ${port}`);
}

bootstrap();
