import { h, render } from 'preact'
import { App } from './app'
import { Router, Route } from 'preact-router';
import { OtherComponent } from './otherComponent';



render((
    <Router>
        <Route path="/" component={App} />
        <Route path="/test" component={OtherComponent} />
    </Router>
), document.body);