import {Card, CardContent, Typography} from "@mui/material";
import React from "react";
import {ArticleDto} from "../../../api";

interface ArticleProps {
    article: ArticleDto
    clickOnCard: (articleId: string) => void
}

export const ArticleCard: React.FC<ArticleProps> = props => {
    const {article} = props;
    return (
        <Card
            style={{width: "400px", cursor: "pointer", borderRadius: "8px"}}
            sx={{boxShadow: 2}}
            onClick={props.clickOnCard.bind(this, article.id)}>
            <CardContent>
                <Typography variant="h5" style={{marginBottom: "0.5rem"}}>
                    {article.name}
                </Typography>
                <Typography variant="body2" color="textSecondary">
                    {article.description}
                </Typography>
                <Typography variant="body1" color="primary" style={{marginTop: "0.5rem"}}>
                    {article.price.toFixed(2)} €
                </Typography>
            </CardContent>
        </Card>
    )
}