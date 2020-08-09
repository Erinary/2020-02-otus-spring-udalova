<template>
    <b-container fluid>
        <b-row>
            <b-col>
                <h1>My Little Library</h1>
            </b-col>
        </b-row>

        <b-row>
            <b-col>
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
            </b-col>
        </b-row>

        <b-row>
            <b-col class="text-right">
                <img class="w-50" src="../assets/cybertruck.jpg" alt="Oops!"/>
            </b-col>
        </b-row>
    </b-container>
</template>

<script>
    export default {
        name: "HomePage"
    }
</script>

<style scoped>

</style>