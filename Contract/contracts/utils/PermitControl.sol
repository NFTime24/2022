// SPDX-License-Identifier: MIT
pragma solidity ^0.8.0;

contract PermitControl {
    mapping(address => bool) private _owners;
    mapping(address => bool) private _minters;

    constructor() {
        _owners[msg.sender] = true;
    }

    modifier onlyOwner(){
        require(_owners[msg.sender], "Only for owners");
        _;
    }

    modifier onlyMinter(){
        require(_owners[msg.sender] || _minters[msg.sender], "Only for minters");
        _;
    }

    function permitOwner(address _to) external onlyOwner {
        _owners[_to] = true;
    }

    function banOwner(address _to) external onlyOwner {
        _owners[_to] = false;
    }

    function isOwner(address _to) external view returns(bool){
        return _owners[_to];
    }

    function permitMinter(address _to) external onlyOwner {
        _minters[_to] = true;
    }

    function banMinter(address _to) external onlyOwner {
        _minters[_to] = false;
    }

    function isMinter(address _to) external view returns(bool){
        return  _owners[_to] || _minters[_to];
    }
}