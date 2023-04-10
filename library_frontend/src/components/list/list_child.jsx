import React, { Component } from 'react';
import {useState} from 'react';
import "./list.css";

function ListChild(name, status){
    console.log(status);
    // const [test, setTest] = useState("");
    // {open ? setTest("OK") : setTest("Tidak")};

    return (
        <div key = {name.name} className = {open ? "customer-open" : "customer"}>
            <span id="customer-name">{name.name}</span>
            {/* <span>{test}</span> */}
        </div> 
    );
}
 
export default ListChild;