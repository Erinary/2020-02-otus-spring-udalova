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
                    <li>
                        <router-link :to="{name: 'books'}">Books</router-link>
                    </li>
                    <li>
                        <router-link :to="{name: 'authors'}">Authors</router-link>
                    </li>
                    <li>
                        <router-link :to="{name: 'genres'}">Genres</router-link>
                    </li>
                </ul>
            </div>
        </div>

        <div class="row">
            <div class="col text-right">
            <!--TODO cybertruck-->
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