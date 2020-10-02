<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <div v-if="purchase">
                <h2 class="jh-entity-heading"><span v-text="$t('balbaursakApp.purchase.detail.title')">Purchase</span> {{purchase.id}}</h2>
                <dl class="row jh-entity-details">
                    <dt>
                        <span v-text="$t('balbaursakApp.purchase.created')">Created</span>
                    </dt>
                    <dd>
                        <span>{{purchase.created}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.purchase.quantity')">Quantity</span>
                    </dt>
                    <dd>
                        <span>{{purchase.quantity}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.purchase.price')">Price</span>
                    </dt>
                    <dd>
                        <span>{{purchase.price}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.purchase.total')">Total</span>
                    </dt>
                    <dd>
                        <span>{{purchase.total}}</span>
                    </dd>
                    <dt>
                        <span v-text="$t('balbaursakApp.purchase.product')">Product</span>
                    </dt>
                    <dd>
                        <div v-if="purchase.product">
                            <router-link :to="{name: 'ProductView', params: {productId: purchase.product.id}}">{{purchase.product.name}}</router-link>
                        </div>
                    </dd>
                </dl>
                <button type="submit"
                        v-on:click.prevent="previousState()"
                        class="btn btn-info">
                    <font-awesome-icon icon="arrow-left"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.back')"> Back</span>
                </button>
                <router-link v-if="purchase.id" :to="{name: 'PurchaseEdit', params: {purchaseId: purchase.id}}" tag="button" class="btn btn-primary">
                    <font-awesome-icon icon="pencil-alt"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.edit')"> Edit</span>
                </router-link>
            </div>
        </div>
    </div>
</template>

<script lang="ts" src="./purchase-details.component.ts">
</script>
