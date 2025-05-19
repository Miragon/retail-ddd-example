import React, {PropsWithChildren} from 'react';

interface ConditionProps {
    condition: boolean;
}

export const If: React.FC<PropsWithChildren<ConditionProps>> = (props) => {
    const {condition, children} = props;
    if (!condition) {
        return null;
    } else {
        return <React.Fragment>{children}</React.Fragment>
    }
}
