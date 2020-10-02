<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="request">
                <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.request.detail.title')">Request</span> {{request.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('balbaursakApp.request.created')">Created</span>
                    </dt>
                    <dd>
                        <span>{{request.created}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.request.customer')">Customer</span>
                    </dt>
                    <dd>
                        <span>{{request.customer}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.request.phone')">Phone</span>
                    </dt>
                    <dd>
                        <span>{{request.phone}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.request.total')">Total</span>
                    </dt>
                    <dd>
                        <span>{{request.total}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.request.complete')">Complete</span>
                    </dt>
                    <dd>
                        <span>{{request.complete}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.request.sales')">Sales</span>
                    </dt>
                    <dd>
                        <span>{{request.sales}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.request.address')">Address</span>
                    </dt>
                    <dd>
                        <span>{{request.address}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="request.id" :to="{name: 'RequestEdit', params: {requestId: request.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./request-details.component.ts">
</script>
