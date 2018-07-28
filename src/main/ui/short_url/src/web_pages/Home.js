import React, { Component } from 'react';
import { TextField } from '../components/TextField'

class Home extends Component {
    render(){
        return(
            <div>
            <TextField name="longURL" label="Enter your URL for shortening" placeholder="Current URL" required="*"/>
            
            <div>
            <p>The URL will be Valid for </p>
            <TextField name="validDays" placeholder="1 (default)"/>
            <p>days.</p>
            </div>
            
            <TextField name="customURL" label="If you have any URL in mind, put it here!" placeholder="Custom URL" required="*"/>
            
            <div>
            <input type="checkbox" id="isSecured"/> Make URL Secure
            <TextField name="securityKey" placeholder="Security Key"/>
            </div>

            </div>
        );
    }
}

export default Home;