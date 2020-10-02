<template>
    <div>
        <h2 id="page-heading">
            <span v-text="$t('balbaursakApp.feedback.home.title')" id="feedback-heading">Feedbacks</span>
            <router-link :to="{name: 'FeedbackCreate'}" tag="button" id="jh-create-entity" class="btn btn-primary float-right jh-create-entity create-feedback">
                <font-awesome-icon icon="plus"></font-awesome-icon>
                <span  v-text="$t('balbaursakApp.feedback.home.createLabel')">
                    Create a new Feedback
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
        <div class="alert alert-warning" v-if="!isFetching && feedbacks && feedbacks.length === 0">
            <span v-text="$t('balbaursakApp.feedback.home.notFound')">No feedbacks found</span>
        </div>
        <div class="table-responsive" v-if="feedbacks && feedbacks.length > 0">
            <table class="table table-striped">
                <thead>
                <tr>
                    <th v-on:click="changeOrder('id')"><span v-text="$t('global.field.id')">ID</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'id'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('created')"><span v-text="$t('balbaursakApp.feedback.created')">Created</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'created'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('customer')"><span v-text="$t('balbaursakApp.feedback.customer')">Customer</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'customer'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('vote')"><span v-text="$t('balbaursakApp.feedback.vote')">Vote</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'vote'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('content')"><span v-text="$t('balbaursakApp.feedback.content')">Content</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'content'"></jhi-sort-indicator></th>
                    <th v-on:click="changeOrder('produce.name')"><span v-text="$t('balbaursakApp.feedback.produce')">Produce</span> <jhi-sort-indicator :current-order="propOrder" :reverse="reverse" :field-name="'produce.name'"></jhi-sort-indicator></th>
                    <th></th>
                </tr>
                </thead>
                <tbody>
                <tr v-for="feedback in feedbacks"
                    :key="feedback.id">
                    <td>
                        <router-link :to="{name: 'FeedbackView', params: {feedbackId: feedback.id}}">{{feedback.id}}</router-link>
                    </td>
                    <td>{{feedback.created}}</td>
                    <td>{{feedback.customer}}</td>
                    <td>{{feedback.vote}}</td>
                    <td>{{feedback.content}}</td>
                    <td>
                        <div v-if="feedback.produce">
                            <router-link :to="{name: 'ProduceView', params: {produceId: feedback.produce.id}}">{{feedback.produce.name}}</router-link>
                        </div>
                    </td>
                    <td class="text-right">
                        <div class="btn-group">
                            <router-link :to="{name: 'FeedbackView', params: {feedbackId: feedback.id}}" tag="button" class="btn btn-info btn-sm details">
                                <font-awesome-icon icon="eye"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.view')">View</span>
                            </router-link>
                            <router-link :to="{name: 'FeedbackEdit', params: {feedbackId: feedback.id}}"  tag="button" class="btn btn-primary btn-sm edit">
                                <font-awesome-icon icon="pencil-alt"></font-awesome-icon>
                                <span class="d-none d-md-inline" v-text="$t('entity.action.edit')">Edit</span>
                            </router-link>
                            <b-button v-on:click="prepareRemove(feedback)"
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
            <span slot="modal-title"><span id="balbaursakApp.feedback.delete.question" v-text="$t('entity.delete.title')">Confirm delete operation</span></span>
            <div class="modal-body">
                <p id="jhi-delete-feedback-heading" v-text="$t('balbaursakApp.feedback.delete.question', {'id': removeId})">Are you sure you want to delete this Feedback?</p>
            </div>
            <div slot="modal-footer">
                <button type="button" class="btn btn-secondary" v-text="$t('entity.action.cancel')" v-on:click="closeDialog()">Cancel</button>
                <button type="button" class="btn btn-primary" id="jhi-confirm-delete-feedback" v-text="$t('entity.action.delete')" v-on:click="removeFeedback()">Delete</button>
            </div>
        </b-modal>
        <div v-show="feedbacks && feedbacks.length > 0">
            <div class="row justify-content-center">
                <jhi-item-count :page="page" :total="queryCount" :itemsPerPage="itemsPerPage"></jhi-item-count>
            </div>
            <div class="row justify-content-center">
                <b-pagination size="md" :total-rows="totalItems" v-model="page" :per-page="itemsPerPage" :change="loadPage(page)"></b-pagination>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./feedback.component.ts">
</script>
