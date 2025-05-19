import React, {PropsWithChildren} from 'react';
import {useAuth0} from "@auth0/auth0-react";
import {LinearProgress} from "@mui/material";

/**
 * This component ensures the user is logged in
 * and provides the authentication context to the rest of the app.
 */
export const LoginBarrier: React.FC<PropsWithChildren> = props => {

    const {isLoading, isAuthenticated, loginWithRedirect, getIdTokenClaims} = useAuth0()

    if (isLoading) {
        return <LinearProgress color="primary"/>
    }

    if (!isAuthenticated) {
        loginWithRedirect({appState: {targetHash: window.location.hash}}).then(() => ({}))
        return <LinearProgress color="primary"/>
    }

    if (isAuthenticated) {
        getIdTokenClaims().then((claims) => {
            const token = claims?.__raw
            token && localStorage.setItem('dddToken', token)
        })
    }

    return (
        <React.Fragment>
            {props.children}
        </React.Fragment>
    )
}