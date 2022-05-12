<template>
  <div>
    <h2>
      <span id='user-management-page-heading' data-cy='userManagementPageHeading'>Users</span>

      <div class='d-flex justify-content-end'>
        <button :disabled='isLoading' class='btn btn-info mr-2' v-on:click='handleSyncList'>
          <font-awesome-icon :spin='isLoading' icon='sync'></font-awesome-icon>
          <span>Refresh List</span>
        </button>
        <router-link v-slot='{ navigate }' :to="{ name: 'EsiUserCreate' }" custom>
          <button class='btn btn-primary jh-create-entity' @click='navigate'>
            <font-awesome-icon icon='plus'></font-awesome-icon>
            <span>Create a new User</span>
          </button>
        </router-link>
      </div>
    </h2>
    <div v-if='users' class='table-responsive'>
      <table aria-describedby='Users' class='table table-striped'>
        <thead>
        <tr>
          <th scope='col' v-on:click="changeOrder('id')">
            <span>ID</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'id'" :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='col' v-on:click="changeOrder('login')">
            <span>Login</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'login'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='col' v-on:click="changeOrder('email')">
            <span>Email</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'email'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='col'></th>
          <th scope='col'><span>Profiles</span></th>
          <th scope='col' v-on:click="changeOrder('createdDate')">
            <span>Created Date</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'createdDate'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='col' v-on:click="changeOrder('lastModifiedBy')">
            <span>Last Modified By</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'lastModifiedBy'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th id='modified-date-sort' scope='col' v-on:click="changeOrder('lastModifiedDate')">
            <span>Last Modified Date</span>
            <jhi-sort-indicator :current-order='propOrder' :field-name="'lastModifiedDate'"
                                :reverse='reverse'></jhi-sort-indicator>
          </th>
          <th scope='col'></th>
        </tr>
        </thead>
        <tbody v-if='users'>
        <tr v-for='user in users' :id='user.login' :key='user.id'>
          <td>
            <router-link :to="{ name: 'EsiUserView', params: { userId: user.login } }">{{ user.id }}</router-link>
          </td>
          <td>{{ user.login }}</td>
          <td class='jhi-user-email'>{{ user.email }}</td>
          <td>
            <button v-if='!user.activated' class='btn btn-danger btn-sm deactivated' v-on:click='setActive(user, true)'>
              Deactivated
            </button>
            <button
              v-if='user.activated'
              :disabled='username === user.login'
              class='btn btn-success btn-sm'
              v-on:click='setActive(user, false)'
            >
              Activated
            </button>
          </td>

          <td>
            <div v-for='authority of user.authorities' :key='authority'>
              <span class='badge badge-info'>{{ authority }}</span>
            </div>
          </td>
          <td>{{ user.createdDate | formatDate }}</td>
          <td>{{ user.lastModifiedBy }}</td>
          <td>{{ user.lastModifiedDate | formatDate }}</td>
          <td class='text-right'>
            <div class='btn-group'>
              <router-link v-slot='{ navigate }' :to="{ name: 'EsiUserView', params: { userId: user.login } }" custom>
                <button class='btn btn-info btn-sm details' @click='navigate'>
                  <font-awesome-icon icon='eye'></font-awesome-icon>
                  <span class='d-none d-md-inline'>View</span>
                </button>
              </router-link>
              <router-link v-slot='{ navigate }' :to="{ name: 'EsiUserEdit', params: { userId: user.login } }" custom>
                <button class='btn btn-primary btn-sm edit' @click='navigate'>
                  <font-awesome-icon icon='pencil-alt'></font-awesome-icon>
                  <span class='d-none d-md-inline'>Edit</span>
                </button>
              </router-link>
              <b-button :disabled='username === user.login' class='btn btn-sm delete' variant='danger'
                        v-on:click='prepareRemove(user)'>
                <font-awesome-icon icon='times'></font-awesome-icon>
                <span class='d-none d-md-inline'>Delete</span>
              </b-button>
            </div>
          </td>
        </tr>
        </tbody>
      </table>
      <b-modal id='removeUser' ref='removeUser' @ok='deleteUser()'>
        <div class='modal-body'>
          <p id='esi-delete-user-heading'>Are you sure you want to delete this user?</p>
        </div>
        <div slot='modal-footer'>
          <button class='btn btn-secondary' type='button' v-on:click='closeDialog()'>Cancel</button>
          <button id='confirm-delete-user' class='btn btn-primary' type='button' v-on:click='deleteUser()'>Delete
          </button>
        </div>
      </b-modal>
    </div>
    <div v-show='users && users.length > 0'>
      <div class='row justify-content-center'>
        <jhi-item-count :itemsPerPage='itemsPerPage' :page='page' :total='queryCount'></jhi-item-count>
      </div>
      <div class='row justify-content-center'>
        <b-pagination v-model='page' :change='loadPage(page)' :per-page='itemsPerPage' :total-rows='totalItems'
                      size='md'></b-pagination>
      </div>
    </div>
  </div>
</template>

<script lang='ts' src='./user-management.component.ts'></script>
