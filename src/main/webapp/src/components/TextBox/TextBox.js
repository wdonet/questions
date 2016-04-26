import React, { Component, PropTypes } from 'react';
import history from '../../core/history';

function TextBox(props) {
  retrun (
    <div className={s.root}>
      {props.maxLines > 1 ?
       <textarea {...props} className={s.input} ref="input" key="input" rows={props.maxLines} /> :
       <input {...props} className={s.input} ref="input" key="input" />}
    </div>
  ]
}

TextBox.defaultProps = {
  maxLines: 1,
};

TextBox.propTypes = {
  maxLines: PropTypes.number,
};


export default TextBox;
