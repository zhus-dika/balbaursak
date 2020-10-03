<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('balbaursakApp.request.home.title')" id="request-heading">Requests</span>
            <router-link :to="{name: 'RequestCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-request">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('balbaursakApp.request.home.createLabel')">
                    Create a new Request
                </span>
            </router-link>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="!isFetching && requests && requests.length === 0">
            <span v-text="$t('balbaursakApp.request.home.notFound')">No requests found</span>
        </div>
        <div class="table-responsive" v-if="requests && requests.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('created')"><span v-text="$t('balbaursakApp.request.created')">Created</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'created'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('customer')"><span v-text="$t('balbaursakApp.request.customer')">Customer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('phone')"><span v-text="$t('balbaursakApp.request.phone')">Phone</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'phone'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('total')"><span v-text="$t('balbaursakApp.request.total')">Total</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'total'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('complete')"><span v-text="$t('balbaursakApp.request.complete')">Complete</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'complete'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('sales')"><span v-text="$t('balbaursakApp.request.sales')">Sales</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'sales'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('address')"><span v-text="$t('balbaursakApp.request.address')">Address</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'address'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="request in requests"
                    :key="request.id">
                    <td>
                        <router-link :to="{name: 'RequestView', params: {requestId: request.id}}">{{request.id}}</router-link>
                    </td>
                    <td>{{request.created}}</td>
                    <td>{{request.customer}}</td>
                    <td>{{request.phone}}</td>
                    <td>{{request.total}}</td>
                    <td>{{request.complete}}</td>
                    <td>{{request.sales}}</td>
                    <td>{{request.address}}</td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'RequestView', params: {requestId: request.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'RequestEdit', params: {requestId: request.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(request)"
                                   variant="danger"
                                   class="btn btn-sm"
                                   v-b-modal.removeEntity>
                                <font-awesome-icon icon="times"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.delete')">Delete</span>
                            </b-button>
                        </div>
                    </td>
                </tr>
                </tbody>
            </table>
        </div>
        <b-modal ref="removeEntity" id="removeEntity" >
            <span slot="modal-title"><span id="balbaursakApp.request.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-request-heading" v-text="$t('balbaursakApp.request.delete.question', {'id': removeId})">Are you sure you want to delete this Request?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-request" v-text="$t('entity.action.delete')" v-on:click="removeRequest()">Delete</button>
            </div>
        </b-modal>
        <div v-show="requests && requests.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./request.component.ts">
</script>
