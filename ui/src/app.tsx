import { h, Component } from "preact";
import { testService, Posts } from "./testService";
import { Link } from "preact-router";

export class App extends Component<any, Posts> {

  constructor() {
    super();
    testService.getPosts().subscribe((posts) => {
      var state: Posts = {
        "posts": posts
      };
      this.setState(state);
    });
  }

  render() {
    let postsView = [<h1>Loading</h1>];
    if (this.state.posts) {
      postsView = this.state.posts.map((post) =>
        <li>{post.title}</li>
      );
    }
    return <div>
      <Link activeClassName="active" href="/">Home</Link>
      <Link activeClassName="active" href="/test">Test</Link>
      <ul>{postsView}</ul>
    </div>;
  }
}