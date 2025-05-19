import {AppBar, Toolbar, Typography} from "@mui/material"
import {LogoutButton} from "./auth/logout-button.tsx";
import {ApplicationRoutes} from "./application-routes.tsx";
import React from "react";

/**
 * This is the main app component.
 * It contains the basic-layout for each page and initializes the router
 */
export const AppContent = () => {
    return (
        <React.Fragment>
            <AppBar position="static">
                <Toolbar>
                    <Typography variant="h6" component="div" sx={{flexGrow: 1}}>
                        My fancy shop
                    </Typography>
                    <LogoutButton color="inherit"/>
                </Toolbar>
            </AppBar>
            <div style={{padding: 25, paddingTop: 35}}>
                <ApplicationRoutes/>
            </div>
        </React.Fragment>
    )
}