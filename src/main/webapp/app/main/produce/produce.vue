<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('balbaursakApp.produce.home.title')" id="produce-heading">Produces</span>
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
        <div class="form-group">
            <label class="form-control-label" v-text="$t('balbaursakApp.produce.category')" for="category">Category</label>
            <select class="form-control" id="category" name="category" v-model="category" required>
                <option v-if="!category" v-bind:value="null" selected></option>
                <option :value="category && categoryOption.id === category.id ? category : categoryOption" v-for="categoryOption in categories" :key="categoryOption.id">{{categoryOption.name}}</option>
            </select>
        </div>
        <div class="table-responsive" v-if="filteredProduces && filteredProduces.length > 0">
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
                    <th v-on:click="changeOrder('category.id')"><span v-text="$t('balbaursakApp.produce.category')">Category</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'category.id'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="produce in filteredProduces"
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
                    </td>
                    <td>
                        <div v-if="produce.category">
                            {{produce.category.name}}
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProduceDetailsBucket', params: {produceId: produce.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
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
