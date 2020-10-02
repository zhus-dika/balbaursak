<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('balbaursakApp.produce.home.title')" id="produce-heading">Produces</span>
            <router-link :to="{name: 'ProduceCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-produce">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('balbaursakApp.produce.home.createLabel')">
                    Create a new Produce
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
        <div class="alert alert-warning" v-if="!isFetching && produces && produces.length === 0">
            <span v-text="$t('balbaursakApp.produce.home.notFound')">No produces found</span>
        </div>
        <div class="table-responsive" v-if="produces && produces.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('name')"><span v-text="$t('balbaursakApp.produce.name')">Name</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'name'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('unit')"><span v-text="$t('balbaursakApp.produce.unit')">Unit</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'unit'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('contains')"><span v-text="$t('balbaursakApp.produce.contains')">Contains</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'contains'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('days')"><span v-text="$t('balbaursakApp.produce.days')">Days</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'days'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('description')"><span v-text="$t('balbaursakApp.produce.description')">Description</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'description'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('price')"><span v-text="$t('balbaursakApp.produce.price')">Price</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'price'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('file')"><span v-text="$t('balbaursakApp.produce.file')">File</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'file'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('category.name')"><span v-text="$t('balbaursakApp.produce.category')">Category</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.name'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="produce in produces"
                    :key="produce.id">
                    <td>
                        <router-link :to="{name: 'ProduceView', params: {produceId: produce.id}}">{{produce.id}}</router-link>
                    </td>
                    <td>{{produce.name}}</td>
                    <td>{{produce.unit}}</td>
                    <td>{{produce.contains}}</td>
                    <td>{{produce.days}}</td>
                    <td>{{produce.description}}</td>
                    <td>{{produce.price}}</td>
                    <td>
                        <a v-if="produce.file" v-on:click="openFile(produce.fileContentType, produce.file)">
                            <img v-bind:src="'data:' + produce.fileContentType + ';base64,' + produce.file" style="max-height: 30px;" alt="produce image"/>
                        </a>
                        <span v-if="produce.file">{{produce.fileContentType}}, {{byteSize(produce.file)}}</span>
                    </td>
                    <td>
                        <div v-if="produce.category">
                            <router-link :to="{name: 'CategoryView', params: {categoryId: produce.category.id}}">{{produce.category.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProduceView', params: {produceId: produce.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'ProduceEdit', params: {produceId: produce.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(produce)"
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
            <span slot="modal-title"><span id="balbaursakApp.produce.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-produce-heading" v-text="$t('balbaursakApp.produce.delete.question', {'id': removeId})">Are you sure you want to delete this Produce?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-produce" v-text="$t('entity.action.delete')" v-on:click="removeProduce()">Delete</button>
            </div>
        </b-modal>
        <div v-show="produces && produces.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./produce.component.ts">
</script>
