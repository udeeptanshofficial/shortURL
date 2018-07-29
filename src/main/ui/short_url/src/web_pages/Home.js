import React, { Component } from 'react';
import { connect } from 'react-redux';
import { reduxForm, Field, formValueSelector } from 'redux-form';
import TextField from '../components/TextField';

import { handleCreateShortURL } from '../store/actions/shortUrlActions';

function validate(values) {
    const errors = {};
    if (!values.longURL) {
        errors.longURL = 'Please enter a valid url.';
    }
    if (!values.numberOfDaysValid || isNaN(values.numberOfDaysValid)) {
        errors.numberOfDaysValid = 'Please enter a valid url.';
    }
    if (values.isSecured && !values.securityKey) {
        errors.securityKey = 'This field is required';
    }

    return errors;
}

class Home extends Component {
    constructor(props) {
        super(props);
        this.onSubmit = this.onSubmit.bind(this);
    }
    componentWillMount() {
        this.props.initialize(this.props.initialValues);
    }
    onSubmit(values) {
        this.props.handleCreateShortURL({ ...values, numberOfDaysValid: parseInt(values.numberOfDaysValid, 10) });
    }
    render() {
        const { formValues, handleSubmit, pristine, actionInProgress, submitting, errorMessage, successData } = this.props;
        const disabled = submitting || actionInProgress || !!successData;
        return (
            <form onSubmit={handleSubmit(this.onSubmit)}>
                <Field
                    required
                    component={TextField}
                    name="longURL"
                    label="Enter your URL for shortening"
                    placeholder="Current URL"
                    disabled={disabled}
                />

                <div>
                    <p>The URL will be Valid for </p>
                    <Field
                        component={TextField}
                        name="numberOfDaysValid"
                        label="Valid for"
                        type="number"
                        placeholder="1"
                        disabled={disabled}
                    />
                    <p>days.</p>
                </div>

                <Field
                    required
                    component={TextField}
                    name="customURL"
                    label="If you have any URL in mind, put it here!"
                    placeholder="Custom URL"
                    disabled={disabled}
                />

                <div>
                    <Field name="isSecured" component="input" type="checkbox" />  Make URL Secure
                    <Field
                        component={TextField}
                        name="securityKey"
                        label="Security Key"
                        type="password"
                        placeholder="******"
                        disabled={disabled || !formValues.isSecured}
                    />
                </div>
                {errorMessage && 
                    <div className="error">
                        <span>{errorMessage}</span>
                    </div>
                }
                <button type="submit" disabled={pristine || disabled}>Submiit</button>

                {successData && 
                    <div className="success">
                        <span>{successData}</span>
                    </div>
                }
            </form>
        );
    }
}

const selector = formValueSelector('CreateForm');

const mapStateToProps = state => ({
    actionInProgress: state.app.actionInProgress,
    successData: state.app.successData,
    errorMessage: state.app.errorMessage,
    initialValues: {
        numberOfDaysValid: 1,
    },
    formValues: {
        isSecured: selector(state, 'isSecured'),
    }
});

export default reduxForm({
    form: 'CreateForm',
    validate
})(
    connect(mapStateToProps, { handleCreateShortURL })(Home)
);