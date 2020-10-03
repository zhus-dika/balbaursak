<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('balbaursakApp.requestpoint.home.title')" id="requestpoint-heading">Requestpoints</span>
        </h2>
        <b-alert :show="dismissCountDown"
            dismissible
            :variant="alertType"
            @dismissed="dismissCountDown=0"
            @dismiss-count-down="countDownChanged">
            {{alertMessage}}
        </b-alert>
        <br/>
        <div class="alert alert-warning" v-if="requestpoints && requestpoints.length === 0">
            <span v-text="$t('balbaursakApp.requestpoint.home.notFound')">No requestpoints found</span>
        </div>
        <div class="table-responsive" v-if="requestpoints && requestpoints.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('quantity')"><span v-text="$t('balbaursakApp.requestpoint.quantity')">Quantity</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'quantity'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('total')"><span v-text="$t('balbaursakApp.requestpoint.total')">Total</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'total'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('produce.name')"><span v-text="$t('balbaursakApp.requestpoint.produce')">Produce</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'produce.name'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="requestpoint in requestpoints"
                    :key="requestpoint.id">
                    <td>
                        <router-link :to="{name: 'RequestpointView', params: {requestpointId: requestpoint.id}}">{{requestpoint.id}}</router-link>
                    </td>
                    <td>{{requestpoint.quantity}}</td>
                    <td>{{requestpoint.total}}</td>
                    <td>
                        <div v-if="requestpoint.produce">
                            {{requestpoint.produce.name}}
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'ProduceDetailsBucket', params: {produceId: requestpoint.produce.id, currentQuantity: requestpoint.quantity}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(requestpoint)"
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
            <span slot="modal-title"><span id="balbaursakApp.requestpoint.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-requestpoint-heading" v-text="$t('balbaursakApp.requestpoint.delete.question', {'id': removeId})">Are you sure you want to delete this Requestpoint?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-requestpoint" v-text="$t('entity.action.delete')" v-on:click="removeRequestpoint()">Delete</button>
            </div>
        </b-modal>
        <button type="submit"
                v-on:click.prevent="previousState()"
                class="btn btn-info">
            <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
        </button>
        <router-link :to="{name: 'RequestSend'}" :disabled="requestpoints.length === 0" tag="button" class="btn btn-primary edit">
            <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
            <span class="d-none d-md-inline" v-text="$t('balbaursakApp.request.addbutton.create')">Send request</span>
        </router-link>
    </div>
</template>

<script lang="ts" src="./requestpoint.component.ts">
</script>
