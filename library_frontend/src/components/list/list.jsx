import {useState, useEffect} from 'react';
import "./list.css";
import 'bootstrap/dist/css/bootstrap.min.css';


function List(bookList){
    const [isEmpty, setIsEmpty] = useState(true);
    const [errorMsg, setErrorMsg] = useState("");
    const [customerClass, setCustomerClass] = useState({"book-item-1": "customer", "book-item-2": "customer", "book-item-3" : "customer"});

    const handleClick = (itemId) => {
        if(customerClass[itemId] == "customer-open"){
            let updatedClass = {"book-item-1": "customer", "book-item-2": "customer", "book-item-3": "customer"};

            setCustomerClass(updatedClass);
            console.log(customerClass);
            return;
        }

        let updatedClass = {"book-item-1": "customer", "book-item-2": "customer", "book-item-3": "customer"};
        updatedClass[itemId] = "customer-open";

        setCustomerClass(updatedClass);
    }

    const displayList = Object.values(bookList.bookList).map((book, index) => {
        const tagName = "book-item-" + (index + 1);

        return(<div id="listContainer">
                    <div key={index} id={tagName} style={{}}>
                        <span id="number">{index + 1}</span>
                        <span id="book-name">{book.name}</span>
                        <img src="src\assets\icons\Vectortoggle.png" className="book-toggle" onClick={() => handleClick(tagName)}/>
                        <p id="author-name">By {book.author}</p>
                        {book.borrower.map((borrowerName) => {
                            return(
                                <div key = {borrowerName} className = {customerClass[tagName]}>
                                    <span id="customer-name">{borrowerName}</span>
                                </div> 
                            )
                        })}
                        <p style={{display: "inline-block"}}></p>
                    </div>
                </div>)
    })

    useEffect(() => {
        if(Object.values(bookList.bookList).length >= 1) setIsEmpty(false);
        else setErrorMsg("No Data Found");
    })

        return (
            <div id="container">
                {isEmpty ? <span id="error-message">{errorMsg}</span> : displayList}
            </div>
        );
}
 
export default List;