import {LinearProgress, Typography} from "@mui/material";
import {useArticles} from "../../hooks/articles.hook.ts";
import React, {useMemo} from "react";
import {useParams} from "react-router";
import {ArticleDto} from "../../api";

export const ArticleDetailsPage: React.FC = () => {

    const {articleId} = useParams<{ articleId: string }>();
    const {data} = useArticles()

    const article = useMemo((): ArticleDto | undefined => {
        const matchingArticle = data?.find((article) => article.id === articleId)
        return matchingArticle ?? undefined
    }, [articleId, data])

    if (!article) {
        return <LinearProgress color="primary"/>
    }

    return (
        <React.Fragment>
            <Typography variant="h4" component="h1" style={{marginBottom: "1rem"}}>
                {article.name}
            </Typography>
            <Typography variant="body1" color="textSecondary" style={{marginBottom: "1rem"}}>
                {article.description}
            </Typography>
            <Typography variant="body1" color="primary" style={{marginTop: "0.5rem"}}>
                {article.price.toFixed(2)} €
            </Typography>
        </React.Fragment>
    )
}