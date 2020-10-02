<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="requestpoint">
                <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.requestpoint.detail.title')">Requestpoint</span> {{requestpoint.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('balbaursakApp.requestpoint.quantity')">Quantity</span>
                    </dt>
                    <dd>
                        <span>{{requestpoint.quantity}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.requestpoint.total')">Total</span>
                    </dt>
                    <dd>
                        <span>{{requestpoint.total}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.requestpoint.produce')">Produce</span>
                    </dt>
                    <dd>
                        <div v-if="requestpoint.produce">
                            <router-link :to="{name: 'ProduceView', params: {produceId: requestpoint.produce.id}}">{{requestpoint.produce.name}}</router-link>
                        </div>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.requestpoint.request')">Request</span>
                    </dt>
                    <dd>
                        <div v-if="requestpoint.request">
                            <router-link :to="{name: 'RequestView', params: {requestId: requestpoint.request.id}}">{{requestpoint.request.id}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="requestpoint.id" :to="{name: 'RequestpointEdit', params: {requestpointId: requestpoint.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./requestpoint-details.component.ts">
</script>
