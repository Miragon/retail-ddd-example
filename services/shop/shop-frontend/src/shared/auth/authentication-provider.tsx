import React, {PropsWithChildren} from 'react';
import {Auth0Provider, AuthorizationParams} from "@auth0/auth0-react";
import type {AppRuntimeConfig} from "../runtime-config.ts";

type AuthenticationProviderProps = PropsWithChildren & {
    runtimeConfig: AppRuntimeConfig;
};

export const AuthenticationProvider: React.FC<AuthenticationProviderProps> = ({runtimeConfig, children}) => {

    const authorizationParams: AuthorizationParams = {
        redirect_uri: window.location.origin,
        ...(runtimeConfig.oauthAudience ? {audience: runtimeConfig.oauthAudience} : {}),
    }

    return (
        <Auth0Provider
            cacheLocation="localstorage"
            useRefreshTokens={true}
            domain={runtimeConfig.oauthDomain}
            clientId={runtimeConfig.oauthClientId}
            authorizationParams={authorizationParams}>
            {children}
        </Auth0Provider>
    );
}
