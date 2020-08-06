<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h2>Book details
                    <router-link :to="{name: 'book-edit', params: {id: book.id}}"
                                 class="btn btn-outline-primary btn-sm" role="button">Edit
                    </router-link>
                </h2>
            </div>
        </div>
        <div class="row mb-2">
            <div class="col">
                <router-link :to="{name: 'books'}" class="btn btn-info btn-sm" role="button">
                    ← To book list
                </router-link>
            </div>
        </div>

        <div class="row mb-2">
            <div class="col">
                <table class="table table-sm">
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Title</th>
                        <th>Year</th>
                        <th>Author</th>
                        <th>Genre</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <td>{{book.id}}</td>
                        <td>{{book.title}}</td>
                        <td>{{book.year}}</td>
                        <td>{{book.author}}</td>
                        <td>{{book.genre}}</td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <h4>Comments</h4>
            </div>
        </div>
        <div class="row">
            <div class="col">
                <div class="alert alert-info" role="alert" v-if="book.comments.length === 0">There are no comments
                    yet
                </div>
                <table class="table" v-else>
                    <thead class="thead-light">
                    <tr>
                        <th>ID</th>
                        <th>Text</th>
                        <th>User</th>
                        <th>Date</th>
                        <th></th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="comment in book.comments" :key="comment.id">
                        <td>{{comment.id}}</td>
                        <td>{{comment.text}}</td>
                        <td>{{comment.user}}</td>
                        <td>{{comment.date.format('YYYY-MM-DD HH:mm:ss')}}</td>
                        <td>
                            <div class="btn btn-outline-danger btn-sm" role="button"
                                 v-on:click="deleteComment(comment.id)">
                                Delete
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
        <div class="row">
            <div class="col-5">
                <form>
                    <div class="form-group">
                        <textarea class="form-control" v-model="newComment.text"
                                  placeholder="Your text here"></textarea>
                    </div>
                </form>
                <form class="form-inline">
                    <div class="form-group">
                        <input class="form-control" v-model="newComment.user">
                    </div>
                    <button class="btn btn-info ml-2" v-on:click="saveComment()">Add new</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    import {client} from '../controller/api.js';
    import moment from 'moment';

    export default {
        name: "BookDetails",

        data() {
            return {
                book: {
                    id: 1,
                    title: "Book Title",
                    year: 1970,
                    author: "John Doe",
                    genre: "Crime fiction",
                    comments: [
                        {
                            id: 1,
                            text: "Comment text here",
                            user: "Guest",
                            date: moment("1970-01-01 00:00:00")
                        }
                    ]
                },
                newComment: {
                    text: "",
                    user: "Guest"
                }
            }
        },

        mounted() {
            this.loadBook(this.$route.params.id);
        },

        methods: {
            loadBook(id) {
                client.getBookDetails(id)
                    .then(book => {
                        this.book = book;
                    });
            },

            deleteComment(id) {
                client.deleteComment(id)
                    .then(() => this.loadBook(this.$route.params.id));
            },

            saveComment() {
                client.saveComment(
                    {
                        text: this.newComment.text,
                        user: this.newComment.user,
                        bookId: this.$route.params.id
                    }
                )
                    .then(() => {
                        this.loadBook(this.$route.params.id);
                        this.newComment.text = "";
                    })
            }
        }
    }
</script>

<style scoped>

</style>