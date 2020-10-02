<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('balbaursakApp.purchase.home.title')" id="purchase-heading">Purchases</span>
            <router-link :to="{name: 'PurchaseCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-purchase">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('balbaursakApp.purchase.home.createLabel')">
                    Create a new Purchase
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
        <div class="alert alert-warning" v-if="!isFetching && purchases && purchases.length === 0">
            <span v-text="$t('balbaursakApp.purchase.home.notFound')">No purchases found</span>
        </div>
        <div class="table-responsive" v-if="purchases && purchases.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('created')"><span v-text="$t('balbaursakApp.purchase.created')">Created</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'created'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('quantity')"><span v-text="$t('balbaursakApp.purchase.quantity')">Quantity</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantity'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('price')"><span v-text="$t('balbaursakApp.purchase.price')">Price</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'price'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('total')"><span v-text="$t('balbaursakApp.purchase.total')">Total</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'total'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('product.name')"><span v-text="$t('balbaursakApp.purchase.product')">Product</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'product.name'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="purchase in purchases"
                    :key="purchase.id">
                    <td>
                        <router-link :to="{name: 'PurchaseView', params: {purchaseId: purchase.id}}">{{purchase.id}}</router-link>
                    </td>
                    <td>{{purchase.created}}</td>
                    <td>{{purchase.quantity}}</td>
                    <td>{{purchase.price}}</td>
                    <td>{{purchase.total}}</td>
                    <td>
                        <div v-if="purchase.product">
                            <router-link :to="{name: 'ProductView', params: {productId: purchase.product.id}}">{{purchase.product.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'PurchaseView', params: {purchaseId: purchase.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'PurchaseEdit', params: {purchaseId: purchase.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(purchase)"
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
            <span slot="modal-title"><span id="balbaursakApp.purchase.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-purchase-heading" v-text="$t('balbaursakApp.purchase.delete.question', {'id': removeId})">Are you sure you want to delete this Purchase?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-purchase" v-text="$t('entity.action.delete')" v-on:click="removePurchase()">Delete</button>
            </div>
        </b-modal>
        <div v-show="purchases && purchases.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./purchase.component.ts">
</script>
