<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="product">
                <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.product.detail.title')">Product</span> {{product.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('balbaursakApp.product.name')">Name</span>
                    </dt>
                    <dd>
                        <span>{{product.name}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.product.unit')">Unit</span>
                    </dt>
                    <dd>
                        <span>{{product.unit}}</span>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="product.id" :to="{name: 'ProductEdit', params: {productId: product.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./product-details.component.ts">
</script>
