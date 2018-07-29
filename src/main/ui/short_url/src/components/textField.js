import React from 'react';
import './components.css';

const TextField = ({ input, meta, disabled, label, placeholder, required, type = 'text' }) => {
    const hasError = meta.touched && meta.error;
    return (
        <div className={hasError ? 'field error' : 'field'}>
            <label>{label}<sup>{required ? '*' : ''}</sup></label>
            <input {...input} disabled={disabled} className="input-text" type={type} placeholder={placeholder} />
            {hasError && <span>{meta.error}</span>}
        </div>
    );
};

export default TextField;