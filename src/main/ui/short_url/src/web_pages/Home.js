import React, { Component } from 'react';
import { connect } from 'react-redux';
import { reduxForm, Field, formValueSelector } from 'redux-form';
import TextField from '../components/TextField';

import { handleCreateShortURL, setSucuessData } from '../store/actions/shortUrlActions';

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
        this.props.handleCreateShortURL({
            ...values, 
            numberOfDaysValid: parseInt(values.numberOfDaysValid, 10),
            securityKey: !values.isSecured ? undefined : values.securityKey
        });
    }
    render() {
        const { formValues, handleSubmit, reset, pristine, actionInProgress, submitting, errorMessage, successData } = this.props;
        const disabled = submitting || actionInProgress || !!successData;
        return (
            <form onSubmit={handleSubmit(this.onSubmit)} className="formWrapper">
                <Field
                    required
                    component={TextField}
                    name="longURL"
                    label="Enter your URL for shortening"
                    placeholder="Current URL"
                    disabled={disabled}
                />

                <Field
                    component={TextField}
                    name="numberOfDaysValid"
                    label="URL validity (in days)"
                    type="number"
                    placeholder="1"
                    disabled={disabled}
                />
                <Field
                    component={TextField}
                    name="customURL"
                    label="If you have any URL in mind, put it here!"
                    placeholder="Custom URL"
                    disabled={disabled}
                />
                <div className="field">
                    <Field name="isSecured" component="input" type="checkbox" disabled={disabled} />  Make URL Secure
                </div>
                {formValues.isSecured &&
                    <Field
                        component={TextField}
                        name="securityKey"
                        label="Security Key"
                        type="password"
                        placeholder="******"
                        disabled={disabled}
                    />
                }
                {errorMessage && 
                    <div className="error text-center">
                        <span>{errorMessage}</span>
                    </div>
                }
                {!successData &&
                    <div className="text-center">
                        <button type="submit" className="submit" disabled={pristine || disabled}>Submiit</button>
                    </div>
                }

                {successData && 
                    <div className="success text-center">
                        <span>{successData}</span>
                    </div>
                }
                {successData && 
                    <div className="text-center">
                        <button
                            onClick={() => {
                                reset();
                                this.props.setSucuessData(null);
                            }}
                            className="text-center"
                        >
                            Reset
                        </button>
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
    connect(mapStateToProps, { handleCreateShortURL, setSucuessData })(Home)
);