<template>
  <div>
    <h2 id='page-heading' data-cy='BookHeading'>
      <span id='book-heading'>Books</span>
      <div class='d-flex justify-content-end'>
        <button :disabled='isFetching' class='btn btn-info mr-2' v-on:click='handleSyncList'>
          <font-awesome-icon :spin='isFetching' icon='sync'></font-awesome-icon>
          <span>Refresh List</span>
        </button>
        <router-link v-slot='{ navigate }' :to="{ name: 'BookCreate' }" custom>
          <button id='jh-create-entity' class='btn btn-primary jh-create-entity create-book'
                  data-cy='entityCreateButton'
                  @click='navigate'>
            <font-awesome-icon icon='plus'></font-awesome-icon>
            <span> Create a new Book </span>
          </button>
        </router-link>
      </div>
    </h2>
    <br />
    <div v-if='!isFetching && books && books.length === 0' class='alert alert-warning'>
      <span>No books found</span>
    </div>
    <div v-if='books && books.length > 0' class='table-responsive'>
      <table aria-describedby='books' class='table table-striped'>
        <thead>
        <tr>
          <th scope='row' v-on:click="changeOrder('id')">
            <span>ID</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'id'" :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('author')">
            <span>Author</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'author'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('title')">
            <span>Title</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'title'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('releaseDate')">
            <span>Release Date</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'releaseDate'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('status')">
            <span>Status</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'status'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('category')">
            <span>Category</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'category'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row' v-on:click="changeOrder('language')">
            <span>Language</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'language'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='row'></th>
        </tr>
        </thead>
        <tbody>
        <tr v-for='book in books' :key='book.id' data-cy='entityTable'>
          <td>
            <router-link :to="{ name: 'BookView', params: { bookId: book.id } }">{{ book.id }}</router-link>
          </td>
          <td>{{ book.author }}</td>
          <td>{{ book.title }}</td>
          <td>{{ book.releaseDate }}</td>
          <td>{{ book.status }}</td>
          <td>{{ book.category }}</td>
          <td>{{ book.language }}</td>
          <td class='text-right'>
            <div class='btn-group'>
              <router-link v-slot='{ navigate }' :to="{ name: 'BookView', params: { bookId: book.id } }" custom>
                <button class='btn btn-info btn-sm details' data-cy='entityDetailsButton' @click='navigate'>
                  <font-awesome-icon icon='eye'></font-awesome-icon>
                  <span class='d-none d-md-inline'>View</span>
                </button>
              </router-link>
              <router-link v-slot='{ navigate }' :to="{ name: 'BookEdit', params: { bookId: book.id } }" custom>
                <button class='btn btn-primary btn-sm edit' data-cy='entityEditButton' @click='navigate'>
                  <font-awesome-icon icon='pencil-alt'></font-awesome-icon>
                  <span class='d-none d-md-inline'>Edit</span>
                </button>
              </router-link>
              <b-button
                v-b-modal.removeEntity
                class='btn btn-sm'
                data-cy='entityDeleteButton'
                variant='danger'
                v-on:click='prepareRemove(book)'
              >
                <font-awesome-icon icon='times'></font-awesome-icon>
                <span class='d-none d-md-inline'>Delete</span>
              </b-button>
            </div>
          </td>
        </tr>
        </tbody>
        <infinite-loading
          v-if='totalItems > itemsPerPage'
          ref='infiniteLoading'
          slot='append'
          :distance='20'
          :identifier='infiniteId'
          force-use-infinite-wrapper='.el-table__body-wrapper'
          @infinite='loadMore'
        >
        </infinite-loading>
      </table>
    </div>
    <b-modal id='removeEntity' ref='removeEntity'>
      <span slot='modal-title'
      ><span id='libraryApp.book.delete.question'
             data-cy='bookDeleteDialogHeading'>Confirm delete operation</span></span
      >
      <div class='modal-body'>
        <p id='esi-delete-book-heading'>Are you sure you want to delete this Book?</p>
      </div>
      <div slot='modal-footer'>
        <button class='btn btn-secondary' type='button' v-on:click='closeDialog()'>Cancel</button>
        <button
          id='esi-confirm-delete-book'
          class='btn btn-primary'
          data-cy='entityConfirmDeleteButton'
          type='button'
          v-on:click='removeBook()'
        >
          Delete
        </button>
      </div>
    </b-modal>
  </div>
</template>

<script lang='ts' src='./book.component.ts'></script>
