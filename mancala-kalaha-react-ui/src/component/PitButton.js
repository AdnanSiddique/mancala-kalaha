// PitButton.js
import React from 'react';
import {Button} from 'antd';

const PitButton = ({ disabled, className, keyx, onClick, stones }) => {
    return (
        <Button disabled={disabled} className={className}  key={keyx} onClick={onClick}>
            {stones}
        </Button>
    );
};

export default PitButton;
