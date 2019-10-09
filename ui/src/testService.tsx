import { ajax } from "rxjs/ajax";
import { Observable } from "rxjs";
import { map } from "rxjs/operators";

export class TestService {

    getPosts(): Observable<Post[]> {
        return ajax({
            url: "https://jsonplaceholder.typicode.com/posts"
        }).pipe(
            map(x => x.response as Post[]));
    }

}

export let testService: TestService = new TestService();

export interface Posts {
    posts: Post[];
}

export interface Post {
    userId: string;
    id: number;
    title: string;
    body: string;
}