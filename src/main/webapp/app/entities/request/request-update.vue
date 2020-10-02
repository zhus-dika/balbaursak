<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="balbaursakApp.request.home.createOrEditLabel" v-text="$t('balbaursakApp.request.home.createOrEditLabel')">Create or edit a Request</h2>
                <div>
                    <div class="form-group" v-if="request.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="request.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.created')" for="request-created">Created</label>
                        <b-input-group class="mb-3">
                            <b-input-group-prepend>
                                <b-form-datepicker
                                    aria-controls="request-created"
                                    v-model="$v.request.created.$model"
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
                            <b-form-input id="request-created" type="text" class="form-control" name="created"  :class="{'valid': !$v.request.created.$invalid, 'invalid': $v.request.created.$invalid }"
                            v-model="$v.request.created.$model"  required />
                        </b-input-group>
                        <div v-if="$v.request.created.$anyDirty && $v.request.created.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.created.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.customer')" for="request-customer">Customer</label>
                        <input type="text" class="form-control" name="customer" id="request-customer"
                            :class="{'valid': !$v.request.customer.$invalid, 'invalid': $v.request.customer.$invalid }" v-model="$v.request.customer.$model"  required/>
                        <div v-if="$v.request.customer.$anyDirty && $v.request.customer.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.customer.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.phone')" for="request-phone">Phone</label>
                        <input type="text" class="form-control" name="phone" id="request-phone"
                            :class="{'valid': !$v.request.phone.$invalid, 'invalid': $v.request.phone.$invalid }" v-model="$v.request.phone.$model"  required/>
                        <div v-if="$v.request.phone.$anyDirty && $v.request.phone.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.phone.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.total')" for="request-total">Total</label>
                        <input type="number" class="form-control" name="total" id="request-total"
                            :class="{'valid': !$v.request.total.$invalid, 'invalid': $v.request.total.$invalid }" v-model.number="$v.request.total.$model"  required/>
                        <div v-if="$v.request.total.$anyDirty && $v.request.total.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.total.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.request.total.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.request.total.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.complete')" for="request-complete">Complete</label>
                        <input type="text" class="form-control" name="complete" id="request-complete"
                            :class="{'valid': !$v.request.complete.$invalid, 'invalid': $v.request.complete.$invalid }" v-model="$v.request.complete.$model"  required/>
                        <div v-if="$v.request.complete.$anyDirty && $v.request.complete.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.complete.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.sales')" for="request-sales">Sales</label>
                        <input type="number" class="form-control" name="sales" id="request-sales"
                            :class="{'valid': !$v.request.sales.$invalid, 'invalid': $v.request.sales.$invalid }" v-model.number="$v.request.sales.$model" />
                        <div v-if="$v.request.sales.$anyDirty && $v.request.sales.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.sales.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.request.sales.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.request.address')" for="request-address">Address</label>
                        <input type="text" class="form-control" name="address" id="request-address"
                            :class="{'valid': !$v.request.address.$invalid, 'invalid': $v.request.address.$invalid }" v-model="$v.request.address.$model"  required/>
                        <div v-if="$v.request.address.$anyDirty && $v.request.address.$invalid">
                            <small class="form-text text-danger" v-if="!$v.request.address.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                        </div>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.request.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./request-update.component.ts">
</script>
