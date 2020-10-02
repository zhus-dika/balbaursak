<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="feedback">
                <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.feedback.detail.title')">Feedback</span> {{feedback.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('balbaursakApp.feedback.created')">Created</span>
                    </dt>
                    <dd>
                        <span>{{feedback.created}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.feedback.customer')">Customer</span>
                    </dt>
                    <dd>
                        <span>{{feedback.customer}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.feedback.vote')">Vote</span>
                    </dt>
                    <dd>
                        <span>{{feedback.vote}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.feedback.content')">Content</span>
                    </dt>
                    <dd>
                        <span>{{feedback.content}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.feedback.produce')">Produce</span>
                    </dt>
                    <dd>
                        <div v-if="feedback.produce">
                            <router-link :to="{name: 'ProduceView', params: {produceId: feedback.produce.id}}">{{feedback.produce.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="feedback.id" :to="{name: 'FeedbackEdit', params: {feedbackId: feedback.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./feedback-details.component.ts">
</script>
