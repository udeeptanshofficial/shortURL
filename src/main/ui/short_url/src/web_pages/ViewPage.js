import React, { Component } from 'react';
import { connect } from 'react-redux';
import { reduxForm, Field } from 'redux-form';
import isEqual from 'lodash/isEqual';
import TextField from '../components/TextField';
import { handleGetShortUrlData, handleVerifySecurityKey } from '../store/actions/shortUrlActions';


function validate(values) {
    const errors = {};
    if (!values.securityKey) {
        errors.securityKey = 'This field is required';
    }
    return errors;
}

class ViewPage extends Component {
    constructor(props) {
        super(props);

        this.state = {
            exists: false,
        }
        this.onSubmit = this.onSubmit.bind(this);
    }
    componentWillMount() {
        console.log(this.props);
        this.props.handleGetShortUrlData(this.props.match.params.shortURL);
    }
    componentWillReceiveProps(nextProps) {
        if (!isEqual(this.props.successData, nextProps.successData)) {
            const data = nextProps.successData;
            if (data.isSecure && !this.state.exists) {
                this.setState({ exists: true });
            } else if (data.longURL) {
                if (data.longURL.startsWith('https://') || data.longURL.startsWith('http://')) {
                    window.location.href = data.longURL;
                } else {
                    window.location.href = 'http://' + data.longURL;
                }
            }
        }
    }

    onSubmit(values) {
        this.props.handleVerifySecurityKey({ ...values, shortURL: this.props.match.params.shortURL });
    }
    render() {
        const { errorMessage, actionInProgress, successData, handleSubmit, submitting, pristine } = this.props;
        const { exists } = this.state;
        const disabled = submitting || actionInProgress;
        return (
            <div>
                {!exists && actionInProgress && <p>Loading....</p>}
                {(!exists && !actionInProgress && errorMessage) && 
                    <div className="error">
                        <span>{errorMessage}</span>
                    </div>
                }

                {(exists && successData && successData.isSecure && !successData.longURL) && 
                    <form onSubmit={handleSubmit(this.onSubmit)} className="formWrapper">
                        <Field
                            required
                            name="securityKey"
                            component={TextField}
                            type="password"
                            label="Security key"
                            placeholder="********"
                            disabled={disabled}
                        />
                        {errorMessage &&
                            <div className="error">
                                <span>{errorMessage}</span>
                            </div>
                        }
                        <div className="text-center">
                            <button type="submit" className="submit" disabled={disabled || pristine}>Submit</button>
                        </div>
                    </form>
                }
            </div>
        );
    }
}

const mapStateToProps = state => ({
    ...state.app,
})

export default reduxForm({
    form: 'ViewPage',
    validate
})(
    connect(mapStateToProps, { handleGetShortUrlData, handleVerifySecurityKey })(ViewPage)
);