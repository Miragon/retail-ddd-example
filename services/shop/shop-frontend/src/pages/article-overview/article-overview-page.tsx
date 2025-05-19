import {useArticles} from "../../hooks/articles.hook.ts";
import {LinearProgress, Typography} from "@mui/material";
import {ArticleCard} from "./components/article-card.tsx";
import React, {useCallback} from "react";
import {If} from "../../shared/if.tsx";
import {useNavigate} from "react-router";

export function ArticleOverviewPage() {

    console.log("ArticleOverviewPage")

    const {status, data, error} = useArticles()
    const navigate = useNavigate()

    const navigateToArticleDetails = useCallback((articleId: string) => {
        navigate(`/articles/${articleId}`)
    }, [])

    return (
        <React.Fragment>

            <Typography variant="h4" component="h1" style={{marginBottom: "1rem"}}>
                All articles
            </Typography>

            <If condition={status === 'pending'}>
                <div style={{textAlign: "center", paddingTop: "2rem"}}>
                    <Typography>Loading articles...</Typography>
                    <LinearProgress color="primary"/>
                </div>
            </If>

            {status === 'error' && (
                <div style={{textAlign: "center", paddingTop: "2rem"}}>
                    <Typography>Error: {(error as Error).message}</Typography>
                </div>
            )}

            {status === 'success' && (
                <React.Fragment>
                    <If condition={data.length === 0}>
                        <div className="text-center text-gray-500">
                            <Typography>No articles available.</Typography>
                        </div>
                    </If>
                    <If condition={data.length > 0}>
                        <div style={{display: "flex", flexDirection: "column", flexWrap: "wrap", gap: "1rem"}}>
                            {data?.map((article) => (
                                <ArticleCard
                                    key={article.id}
                                    article={article}
                                    clickOnCard={navigateToArticleDetails}
                                />
                            ))}
                        </div>
                    </If>
                </React.Fragment>
            )}
        </React.Fragment>
    )
}