<template>
    <div class="container-fluid">
        <div class="row">
            <div class="col">
                <h1>My Little Library</h1>
            </div>
        </div>

        <div class="row">
            <div class="col">
                <ul>
                    <li><a href="books.html" th:href="@{/library/books}">Books</a></li>
                    <li>
                        <router-link :to="{name: 'authors'}">Authors</router-link>
                    </li>
                    <li><a href="genres.html" th:href="@{/library/genres}">Genres</a></li>
                </ul>
            </div>
        </div>

        <div class="row">
            <div class="col text-right">
                <img class="w-50" th:src="@{/img/cybertruck.jpg}" alt="Oops!"/>
            </div>
        </div>
    </div>
</template>

<script>
    export default {
        name: "HomePage"
    }
</script>

<style scoped>

</style>