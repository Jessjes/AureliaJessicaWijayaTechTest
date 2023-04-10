import React, { useState, useEffect } from 'react';
import "./button.css";

function Button() {
    const [country, setCountry] = useState("");
    const [books, setBooks] = useState({});
    const urlCountry = "http://localhost:8090/people/getRandomCountry";
    const urlBook = "http://localhost:8090/bookrents/getTop3RentedBookCountry?country_code="

    const handleClick = () =>{
        fetch(urlCountry, {
            method: "GET",
            headers: {"Content-Type" : "application/json"}
        })
        .then((response) => response.json())
        .then(async (data) => {
            var url = urlBook + data[0].country.country_code;

            fetch(url, {
                        method: "GET",
                        headers: {"Content-Type" : "application/json"}})
            .then((response) => response.json())
            .then((data) => {
                                setBooks(data);                  
                            })
            .catch((error) => console.log(error));

            setCountry(data[0].country.country_code);
            
        })
        .catch((error) => console.log(error));
    };

    useEffect(() => {
        console.log(books);
    }, [books]);

        return (
            <button id="action-btn" onClick={handleClick}>
                Get Country: {country}
            </button>
        );
    }
 
export default Button;