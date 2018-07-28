import React, { Component } from 'react';
import './components.css';

export class TextField extends Component {
    render() {
        return (
            <div>
                <p>{this.props.label}<sup>{this.props.required}</sup></p>
                <input className="input-text" type="text" name={this.props.name} placeholder={this.props.placeholder} value={this.props.value}/>
            </div>
        );
    }
}