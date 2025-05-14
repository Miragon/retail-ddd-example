import React, {PropsWithChildren} from 'react';
import {Auth0Provider, AuthorizationParams} from "@auth0/auth0-react";

const CLIENT_ID = "22pHgiuTCCLFKHK7SMbWkCzohwar8knS"
const DOMAIN = "retail-ddd-example.eu.auth0.com"

export const AuthenticationProvider: React.FC<PropsWithChildren> = props => {

    const authorizationParams: AuthorizationParams = {
        redirect_uri: window.location.origin,
    }

    return (
        <Auth0Provider
            cacheLocation="localstorage"
            useRefreshTokens={true}
            domain={DOMAIN}
            clientId={CLIENT_ID}
            authorizationParams={authorizationParams}>
            {props.children}
        </Auth0Provider>
    );
}