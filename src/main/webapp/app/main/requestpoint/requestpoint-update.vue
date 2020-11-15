<template>
    <div class="row justify-content-center">
        <div class="col-8">
            <form name="editForm" role="form" novalidate v-on:submit.prevent="save()" >
                <h2 id="balbaursakApp.requestpoint.home.createOrEditLabel" v-text="$t('balbaursakApp.requestpoint.home.createOrEditLabel')">Create or edit a Requestpoint</h2>
                <div>
                    <div class="form-group" v-if="requestpoint.id">
                        <label for="id" v-text="$t('global.field.id')">ID</label>
                        <input type="text" class="form-control" id="id" name="id"
                               v-model="requestpoint.id" readonly />
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.requestpoint.quantity')" for="requestpoint-quantity">Quantity</label>
                        <input type="number" class="form-control" name="quantity" id="requestpoint-quantity"
                            :class="{'valid': !$v.requestpoint.quantity.$invalid, 'invalid': $v.requestpoint.quantity.$invalid }" v-model.number="$v.requestpoint.quantity.$model"  required/>
                        <div v-if="$v.requestpoint.quantity.$anyDirty && $v.requestpoint.quantity.$invalid">
                            <small class="form-text text-danger" v-if="!$v.requestpoint.quantity.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.requestpoint.quantity.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.requestpoint.quantity.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.requestpoint.total')" for="requestpoint-total">Total</label>
                        <input type="number" class="form-control" name="total" id="requestpoint-total"
                            :class="{'valid': !$v.requestpoint.total.$invalid, 'invalid': $v.requestpoint.total.$invalid }" v-model.number="$v.requestpoint.total.$model"  required/>
                        <div v-if="$v.requestpoint.total.$anyDirty && $v.requestpoint.total.$invalid">
                            <small class="form-text text-danger" v-if="!$v.requestpoint.total.required" v-text="$t('entity.validation.required')">
                                This field is required.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.requestpoint.total.min" v-text="$t('entity.validation.min', {min: 0})">
                                This field should be at least 0.
                            </small>
                            <small class="form-text text-danger" v-if="!$v.requestpoint.total.numeric" v-text="$t('entity.validation.number')">
                                This field should be a number.
                            </small>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.requestpoint.produce')" for="requestpoint-produce">Produce</label>
                        <select class="form-control" id="requestpoint-produce" name="produce" v-model="requestpoint.produce" required>
                            <option v-if="!requestpoint.produce" v-bind:value="null" selected></option>
                            <option v-bind:value="requestpoint.produce && produceOption.id === requestpoint.produce.id ? requestpoint.produce : produceOption" v-for="produceOption in produces" :key="produceOption.id">{{produceOption.name}}</option>
                        </select>
                    </div>
                    <div v-if="$v.requestpoint.produce.$anyDirty && $v.requestpoint.produce.$invalid">
                        <small class="form-text text-danger" v-if="!$v.requestpoint.produce.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                    <div class="form-group">
                        <label class="form-control-label" v-text="$t('balbaursakApp.requestpoint.request')" for="requestpoint-request">Request</label>
                        <select class="form-control" id="requestpoint-request" name="request" v-model="requestpoint.request" required>
                            <option v-if="!requestpoint.request" v-bind:value="null" selected></option>
                            <option v-bind:value="requestpoint.request && requestOption.id === requestpoint.request.id ? requestpoint.request : requestOption" v-for="requestOption in requests" :key="requestOption.id">{{requestOption.id}}</option>
                        </select>
                    </div>
                    <div v-if="$v.requestpoint.request.$anyDirty && $v.requestpoint.request.$invalid">
                        <small class="form-text text-danger" v-if="!$v.requestpoint.request.required" v-text="$t('entity.validation.required')">
                            This field is required.
                        </small>
                    </div>
                </div>
                <div>
                    <button type="button" id="cancel-save" class="btn btn-secondary" v-on:click="previousState()">
                        <font-awesome-icon icon="ban"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.cancel')">Cancel</span>
                    </button>
                    <button type="submit" id="save-entity" :disabled="$v.requestpoint.$invalid || isSaving" class="btn btn-primary">
                        <font-awesome-icon icon="save"></font-awesome-icon>&nbsp;<span v-text="$t('entity.action.save')">Save</span>
                    </button>
                </div>
            </form>
        </div>
    </div>
</template>
<script lang="ts" src="./requestpoint-update.component.ts">
</script>
