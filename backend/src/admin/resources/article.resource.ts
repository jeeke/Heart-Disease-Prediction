import {ResourceWithOptions} from "admin-bro";
import {Article} from "../../models/article.entity";

const ArticleResource: ResourceWithOptions = {
    resource: Article,
    options: {},
};

export default ArticleResource;
