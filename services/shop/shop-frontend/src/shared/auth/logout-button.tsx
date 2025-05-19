import {useAuth0} from "@auth0/auth0-react";
import {Button} from "@mui/material";
import React, {PropsWithChildren} from "react";

interface Props {
    color?: "inherit" | "primary"
}

export const LogoutButton: React.FC<PropsWithChildren<Props>> = props => {

    const {logout} = useAuth0();
    const performLogout = () => logout({
        logoutParams: {returnTo: window.location.origin}
    });

    return (
        <Button color={props.color} onClick={performLogout}>
            Logout
        </Button>
    );
}