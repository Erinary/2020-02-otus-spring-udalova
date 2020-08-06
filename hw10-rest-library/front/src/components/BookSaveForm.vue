<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h2>Book info</h2>
            </div>
        </div>
        <div class="row">
            <div class="col-6">
                <form>
                    <div class="form-group">
                        <label for="bookTitle">Title </label>
                        <input class="form-control" id="bookTitle" type="text" v-model="book.title">
                    </div>
                    <div class="form-group">
                        <label for="bookYear">Year</label>
                        <input class="form-control" id="bookYear" type="number" v-model="book.year">
                    </div>
                    <div class="form-group">
                        <label for="bookAuthor">Author</label>
                        <input class="form-control" id="bookAuthor" type="text" v-model="book.author">
                    </div>
                    <div class="form-group">
                        <label for="bookGenre">Genre</label>
                        <input class="form-control" id="bookGenre" type="text" v-model="book.genre">
                    </div>
                    <div>
                        <button v-on:click="saveBook()" class="btn btn-info btn-sm">Save</button>
                        <button v-on:click="back()" class="btn btn-info btn-sm ml-2">Cancel</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
    import {client} from '../controller/api.js';

    export default {
        name: "BookSaveForm",

        data() {
            return {
                book: {
                    title: "Title",
                    year: 1970,
                    author: "Author",
                    genre: "Genre"
                }
            }
        },

        mounted() {
            const bookId = this.getBookIdFromRoute()
            if (bookId !== null) {
                this.loadBook(bookId);
            }
        },

        methods: {
            getBookIdFromRoute() {
                return (typeof this.$route.params.id !== 'undefined') ? this.$route.params.id : null;
            },

            loadBook(id) {
                client.getBookDetails(id)
                    .then(book => {
                        this.book = book;
                    });
            },

            saveBook() {
                const id = this.getBookIdFromRoute();
                client.saveBook({
                    id: id,
                    title: this.book.title,
                    year: this.book.year,
                    author: this.book.author,
                    genre: this.book.genre
                })
                    .then(() => {
                        if (id !== null) {
                            this.$router.push({name: 'book-details', params: {id: id}});
                        } else {
                            this.$router.push({name: 'books'});
                        }
                    });
            },

            back() {
                history.back();
            }
        }
    }
</script>

<style scoped>

</style>