import React, { Component, PropTypes } from 'react';
import s from './ContentPage.scss';
import withStyles from '../../decorators/withStyles';

@withStyles(s)
class ContentPage extends Component {

  static propTypes = {
    path: PropTypes.string.isRequired,
    content: PropTypes.string.isRequired,
    title: PropTypes.string,
  };

  static contextTypes = {
    onSetTitle: PropTypes.func.isRequired,
  };

  render() {
    this.context.onSetTitle(this.props.title);
    return (
      <div className={s.suggestionWrapper}>
        <div dangerouslySetInnerHTML={{ __html: this.props.content || '' }} />
      </div>
    );
  }

}

export default ContentPage;
