<template>
  <div class='row justify-content-center'>
    <div class='col-8'>
      <div v-if='book'>
        <h2 class='jh-entity-heading' data-cy='bookDetailsHeading'><span>Book</span> {{ book.id }}</h2>
        <dl class='row jh-entity-details'>
          <dt>
            <span>Author</span>
          </dt>
          <dd>
            <span>{{ book.author }}</span>
          </dd>
          <dt>
            <span>Title</span>
          </dt>
          <dd>
            <span>{{ book.title }}</span>
          </dd>
          <dt>
            <span>Release Date</span>
          </dt>
          <dd>
            <span>{{ book.releaseDate }}</span>
          </dd>
          <dt>
            <span>Status</span>
          </dt>
          <dd>
            <span>{{ book.status }}</span>
          </dd>
          <dt>
            <span>Category</span>
          </dt>
          <dd>
            <span>{{ book.category }}</span>
          </dd>
          <dt>
            <span>Language</span>
          </dt>
          <dd>
            <span>{{ book.language }}</span>
          </dd>
        </dl>
        <button class='btn btn-info' data-cy='entityDetailsBackButton' type='submit'
                v-on:click.prevent='previousState()'>
          <font-awesome-icon icon='arrow-left'></font-awesome-icon>&nbsp;<span> Back</span>
        </button>
        <router-link v-if='book.id' v-slot='{ navigate }' :to="{ name: 'BookEdit', params: { bookId: book.id } }"
                     custom>
          <button class='btn btn-primary' @click='navigate'>
            <font-awesome-icon icon='pencil-alt'></font-awesome-icon>&nbsp;<span> Edit</span>
          </button>
        </router-link>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./book-details.component.ts'></script>
