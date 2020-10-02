<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="balbaursakApp.purchase.home.createOrEditLabel" v-text="$t('balbaursakApp.purchase.home.createOrEditLabel')">Create or edit a Purchase</h2>
                <div>
                    <div class="form-group" v-if="purchase.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="purchase.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.purchase.created')" for="purchase-created">Created</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="purchase-created"
                                    v-model="$v.purchase.created.$model"
                                    name="created"
                                    class="form-control"
                                    :locale="currentLanguage"
                                    button-only
                                    today-button
                                    reset-button
                                    close-button
                                >
                                </b-form-datepicker>
                            </b-input-group-prepend>
                            <b-form-input id="purchase-created" type="text" class="form-control" name="created"  :class="{'valid': !$v.purchase.created.$invalid, 'invalid': $v.purchase.created.$invalid }"
                            v-model="$v.purchase.created.$model"  required />
                        </b-input-group>
                        <div v-if="$v.purchase.created.$anyDirty && $v.purchase.created.$invalid">
                            <small class="form-text text-danger" v-if="!$v.purchase.created.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.purchase.quantity')" for="purchase-quantity">Quantity</label>
                        <input type="number" class="form-control" name="quantity" id="purchase-quantity"
                            :class="{'valid': !$v.purchase.quantity.$invalid, 'invalid': $v.purchase.quantity.$invalid }" v-model.number="$v.purchase.quantity.$model"  required/>
                        <div v-if="$v.purchase.quantity.$anyDirty && $v.purchase.quantity.$invalid">
                            <small class="form-text text-danger" v-if="!$v.purchase.quantity.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.purchase.quantity.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.purchase.quantity.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.purchase.price')" for="purchase-price">Price</label>
                        <input type="number" class="form-control" name="price" id="purchase-price"
                            :class="{'valid': !$v.purchase.price.$invalid, 'invalid': $v.purchase.price.$invalid }" v-model.number="$v.purchase.price.$model"  required/>
                        <div v-if="$v.purchase.price.$anyDirty && $v.purchase.price.$invalid">
                            <small class="form-text text-danger" v-if="!$v.purchase.price.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.purchase.price.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.purchase.price.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.purchase.total')" for="purchase-total">Total</label>
                        <input type="number" class="form-control" name="total" id="purchase-total"
                            :class="{'valid': !$v.purchase.total.$invalid, 'invalid': $v.purchase.total.$invalid }" v-model.number="$v.purchase.total.$model"  required/>
                        <div v-if="$v.purchase.total.$anyDirty && $v.purchase.total.$invalid">
                            <small class="form-text text-danger" v-if="!$v.purchase.total.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.purchase.total.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.purchase.total.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.purchase.product')" for="purchase-product">Product</label>
                        <select class="form-control" id="purchase-product" name="product" v-model="purchase.product" required>
                            <option v-if="!purchase.product" v-bind:value="null" selected></option>
                            <option v-bind:value="purchase.product && productOption.id === purchase.product.id ? purchase.product : productOption" v-for="productOption in products" :key="productOption.id">{{productOption.name}}</option>
                        </select>
                    </div>
                    <div v-if="$v.purchase.product.$anyDirty && $v.purchase.product.$invalid">
                        <small class="form-text text-danger" v-if="!$v.purchase.product.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.purchase.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./purchase-update.component.ts">
</script>
